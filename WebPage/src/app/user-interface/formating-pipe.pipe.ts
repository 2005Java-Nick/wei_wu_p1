import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatingPipe'
})
export class FormatingPipePipe implements PipeTransform {

  transform(value: any): any {
    if (value == null || value === undefined) {
      return 'N/A';
    } else {
      return value;
    }
  }

}
