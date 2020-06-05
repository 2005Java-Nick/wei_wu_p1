// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  loginURL: 'http://localhost:8080/TRMS-WebApp/api/login',
  formDataURL: 'http://localhost:8080/TRMS-WebApp/FormDataServlet',
  mySubmissionsUrl: 'http://localhost:8080/TRMS-WebApp/MySubmissions',
  adminUrl: 'http://localhost:8080/TRMS-WebApp/AdminServlet',
  fileDownloadUrl: 'http://localhost:8080/TRMS-WebApp/FileDownloadServlet',
  messageUploadUrl: 'http://localhost:8080/TRMS-WebApp/MessageUploadServlet',
  employeeInfoUrl: 'http://localhost:8080/TRMS-WebApp/EmployeeInfoServlet',
  approvalUrl: 'http://localhost:8080/TRMS-WebApp/ApprovalServlet'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
