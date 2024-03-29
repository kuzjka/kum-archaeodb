import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {StepperSelectionEvent} from "@angular/cdk/stepper";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ItemParsingRequestDto, ParsedItem} from "../items.model";
import {Router} from "@angular/router";
import {ItemsService} from "../../items.service";

@Component({
  selector: 'app-items-import',
  templateUrl: './items-import.component.html',
  styleUrls: ['./items-import.component.css']
})
export class ItemsImportComponent implements OnInit {

  csvField = new FormControl('')
  delimiterField = new FormControl('TAB')
  overwriteField = new FormControl(false)
  commaDecimalSeparatorsField = new FormControl('TRUE')
  loading: boolean = false;

  parsedItems: Array<ParsedItem> = [];
  parsedItemsFiltered: Array<ParsedItem> = [];
  parsedItemColumns = ['pointNumber', 'name', 'category', 'latitude', 'longitude', 'hectare', 'dimensions',
    'weight', 'remarks', 'gpsPoint', 'caliber', 'deformation'];
  parsedItemControls: Array<ParsedItemFormControls> = [];
  bulletControls: Array<ParsedItemFormControls> = [];
  duplicates: Array<ParsedItem> = [];

  categories!: string [];

  bulletCategory = 'Кулі';

  constructor(private snackBar: MatSnackBar, private router: Router, private service: ItemsService) {
  }

  ngOnInit(): void {
    this.getCategories();
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const fileReader = new FileReader();
      fileReader.onload = () => this.csvField.setValue(fileReader.result as string)
      fileReader.onerror = () => this.snackBar.open("Cannot open file", undefined, {
        duration: 3000
      });
      fileReader.readAsText(file);
    }
  }

  onStepperChange(event: StepperSelectionEvent): void {
    if (event.previouslySelectedIndex === 0 && this.parsedItems.length === 0) this.parse();

    if (event.selectedIndex === 4)
      this.bulletControls = this.parsedItemControls.filter(i => i.data.category === this.bulletCategory);

    if (event.selectedIndex === 5)
      this.parsedItemsFiltered = this.overwriteField.value ? this.parsedItems
        : this.parsedItems.filter(i => !i.numberExists);
  }

  parse(): void {
    console.log(`Sending parse data: text length: ${this.csvField.value?.length}, delimiter: ${this.delimiterField.value}`);
    this.loading = true;
    this.service.parse(new ItemParsingRequestDto(this.csvField.value,
      this.delimiterField.value,
      this.commaDecimalSeparatorsField.value))
      .subscribe(items => {
        this.parsedItems = items;
        this.overwriteField.setValue(false);
        this.parsedItemsFiltered = items.filter(i => !i.numberExists);
        this.duplicates = items.filter(i => i.numberExists);
        this.parsedItemControls = this.createFormControls(this.parsedItemsFiltered);
        this.loading = false;
      });
  }

  onOverwriteChanged(): void {
    this.parsedItemsFiltered = this.overwriteField.value ? this.parsedItems
      : this.parsedItems.filter(i => !i.numberExists);
    this.parsedItemControls = this.createFormControls(this.parsedItemsFiltered);
  }

  save(): void {
    this.service.addParsed(this.parsedItems).subscribe(data => {
      const count = this.parsedItemsFiltered.length;
      this.snackBar.open(`Додано ${data} ${this.makePluralItems(data)}`, undefined, {duration: 3000})
      this.router.navigate(['items'])
      console.log("Saving data:\n" + JSON.stringify(this.parsedItemsFiltered, null, 2));
    })
  }

  createFormControls(items: Array<ParsedItem>): Array<ParsedItemFormControls> {
    return items.map(i =>
      ({
        data: i,
        category: new FormControl(i.category),
        hectare: new FormControl(i.hectare),
        caliber: new FormControl((i.caliberApproximate ? '~' : '') + i.caliber),
        deformation: new FormControl(i.deformation),
      }));
  }

  getCategories() {
    this.service.getCategoryNames().subscribe(data => {
      this.categories = data;
    });
  }

  onCategoryChanged(controls: ParsedItemFormControls): void {
    controls.data.categoryAutodetected = false;
    controls.data.category = controls.category.value;
  }

  onHectareChanged(controls: ParsedItemFormControls): void {
    controls.data.hectareAutodetected = false;
    controls.data.hectare = controls.hectare.value;
  }

  onCaliberChanged(controls: ParsedItemFormControls): void {
    controls.data.caliberAutodetected = false;
    controls.data.caliberApproximate = controls.caliber.value.startsWith('~');
    controls.data.caliber = controls.caliber.value.replaceAll('~', '');
  }

  onDeformationChanged(controls: ParsedItemFormControls): void {
    controls.data.deformationAutodetected = false;
    controls.data.deformation = controls.deformation.value;
  }

  makePluralItems(n: number): string {
    const remainder = n % 10;

    if (remainder > 4 || remainder === 0 || (n > 10 && n < 15))
      return "нових предметів";

    if (remainder === 1)
      return "новий предмет";

    return "нових предмети";
  }
}

interface ParsedItemFormControls {
  data: ParsedItem,
  category: FormControl,
  hectare: FormControl,
  caliber: FormControl,
  deformation: FormControl
}
