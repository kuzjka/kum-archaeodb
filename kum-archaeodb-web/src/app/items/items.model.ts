import { Location } from "../commons.model";

export class ItemsRequest {
  page: number;
  size: number;
  categories: string | undefined;
  sort: string | undefined;
  order: 'asc' | 'desc' | undefined;

  constructor(page: number, size: number) {
    this.page = page;
    this.size = size;
  }
}

export interface Item {
  name: string;
  year?: number;
  pointNumber: string;
  hectare?: number;
  depth?: number;
  location: Location;
  description?: string;
  material?: string;
  technique?: string;
  condition?: string;
  dimensions?: string;
  weight?: number;
  category?: string;
  remarks?: string;
  context?: number;
  image?: string;
  museumNumber?: string;
  gpsPoint?: string;
}

export interface Bullet extends Item {
  caliber?: number;
  approximate?: boolean;
  standard?: string;
  deformation?: 'NONE' | 'LIGHT' | 'HARD';
  imprints?: boolean;
}

export interface ParsedItem {
  name: string;
  pointNumber: string;
  location: Location;
  hectare: number;
  dimensions: string;
  weight: number;
  remarks: string;
  gpsPoint: string;
  category: string;
  caliber: number;
  caliberApproximate: boolean;
  deformation: 'NONE' | 'LIGHT' | 'HARD';
  numberExists: boolean;
  categoryAutodetected: boolean;
  caliberAutodetected: boolean;
  deformationAutodetected: boolean;
  hectareAutodetected: boolean;
  save: boolean;
}


