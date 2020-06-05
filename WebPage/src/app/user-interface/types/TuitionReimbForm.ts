import { Forms } from './Forms';

export class TuitionReimbForm extends Forms{
    eventLocation: string;
    eventDiscription: string;
    eventCost: number;
    eventType: string;
    justification: string;
    workTimeOff: string;
    gradingFormat: string;
    cutOffGrade: string;
    eventDate: string;
    optionalComments: string;
    reimbursementAvaliable: number;
    reimbursementPending: number;
}
