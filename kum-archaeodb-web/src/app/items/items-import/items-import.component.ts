import { Component, OnInit } from '@angular/core';
import { FormControl } from "@angular/forms";
import { StepperSelectionEvent } from "@angular/cdk/stepper";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ParsedItem } from "../items.model";
import { delay, of } from "rxjs";
import { Router } from "@angular/router";
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

  constructor (private snackBar: MatSnackBar, private router: Router, private service:ItemsService) {}

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
    this.service.parse(this.csvField.value).subscribe(items =>
    {


      this.parsedItems = items;
      alert(this.parsedItems);
      this.overwriteField.setValue(false);
      this.parsedItemsFiltered = items.filter(i => !i.numberExists);
      this.duplicates = items.filter(i => i.numberExists);
      this.parsedItemControls = this.createFormControls(this.parsedItemsFiltered);
      this.loading = false;

  })};

  onOverwriteChanged(): void {
    this.parsedItemsFiltered = this.overwriteField.value ? this.parsedItems
      : this.parsedItems.filter(i => !i.numberExists);
    this.parsedItemControls = this.createFormControls(this.parsedItemsFiltered);
  }

  save(): void {
    console.log("Saving data:\n" + JSON.stringify(this.parsedItemsFiltered, null, 2));
    const count = this.parsedItemsFiltered.length;
    this.snackBar.open(`Додано ${count} ${this.makePluralItems(count)}`, undefined, { duration: 3000 })
    this.router.navigate(['items'])
  }

  generateParsedItems(): Array<ParsedItem> {
    return [
      {
        name: "Куля свинцева",
        pointNumber: "145",
        location: { latitude: 12.34, longitude: 23.45 },
        hectare: 4,
        dimensions: "12x13x14",
        weight: 17,
        remarks: "remarks",
        gpsPoint: "234н",
        category: "Кулі",
        caliber: 13,
        caliberApproximate: false,
        deformation: "NONE",
        numberExists: false,
        categoryAutodetected: true,
        caliberAutodetected: true,
        deformationAutodetected: false,
        hectareAutodetected: true,
        save: true
      },
      {
        name: "Куля свинцева деформована",
        pointNumber: "149",
        location: { latitude: 11.22, longitude: 22.33 },
        dimensions: "15x16x11",
        weight: 17,
        remarks: "remarks",
        gpsPoint: "11",
        category: "Кулі",
        caliber: 16,
        caliberApproximate: true,
        deformation: "LIGHT",
        numberExists: true,
        categoryAutodetected: true,
        caliberAutodetected: false,
        deformationAutodetected: true,
        hectareAutodetected: false,
        save: true
      },
      {
        name: "Вухналь залізний",
        pointNumber: "236/2",
        location: { latitude: 21.43, longitude: 32.54 },
        hectare: 3,
        dimensions: "42х12",
        weight: 27,
        remarks: "remarks",
        gpsPoint: "432в",
        category: "Спорядження вершника",
        caliber: -1,
        caliberApproximate: false,
        deformation: "NONE",
        numberExists: false,
        categoryAutodetected: true,
        caliberAutodetected: false,
        deformationAutodetected: false,
        hectareAutodetected: true,
        save: true
      }
    ];
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

  getCategories()  {
    this.service.getCategoryNames().subscribe(data=>{
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
