export interface Page<T> {
  totalCount: number;
  totalPages: number;
  content: T[];
}

export interface Location {
  latitude: number;
  longitude: number;
}

export interface ErrorResponse {
  message: string;
}
