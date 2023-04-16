import { Component } from '@angular/core';
import { FormBuilder, FormControl } from "@angular/forms";
import { StepperSelectionEvent } from "@angular/cdk/stepper";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ParsedItem } from "../items.model";
import { Location } from "../../commons.model";

@Component({
  selector: 'app-items-import',
  templateUrl: './items-import.component.html',
  styleUrls: ['./items-import.component.css']
})
export class ItemsImportComponent {

  csvField = new FormControl('')
  delimiterField = new FormControl('TAB')

  loading: boolean = false;
  parsedItems: Array<ParsedItem> = [];
  parsedItemColumns = ['pointNumber', 'name', 'category', 'latitude', 'longitude', 'hectare', 'dimensions',
    'weight', 'remarks', 'gpsPoint', 'caliber', 'deformation'];

  constructor (private snackBar: MatSnackBar) {}

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
    if (event.selectedIndex === 1) this.parse();
  }

  parse(): void {
    console.log(`Sending parse data: text length: ${this.csvField.value?.length}, delimiter: ${this.delimiterField.value}`);
    this.generateParsedItems();
  }

  save(): void {
    console.log("Saving data:\n" + JSON.stringify(this.parsedItems));
  }

  generateParsedItems(): void {
    this.loading = true;
    setTimeout(() => {
      this.parsedItems = [
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
          caliber: 14,
          caliberApproximate: true,
          deformation: "NONE",
          numberExists: false,
          categoryAutodetected: true,
          caliberAutodetected: true,
          deformationAutodetected: false,
          hectareAutodetected: true,
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
      this.loading = false;
    }, 3000);
  }
}
