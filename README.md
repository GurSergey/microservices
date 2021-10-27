# microservices
Contains several modules:
* client-module - contains inforamtion about client of organization
* materials-module - contains inforamtion about used in organizations materials and resources
* cutter-module -  contains inforamtion about process cutting papper and the employees involved in this process
* envelope-module - contains inforamtion about process envelope packing process and the employees involved in this process
* order-module - contains inforamtion about  fulfillment of  order, which is a conveyor process
* packaging-module -  contains inforamtion about process packaging and the employees involved in this process
* preparation-document-module - contains information about reports include files of reports
* printer-module - prepared and not prepared files for printing
* report-module - contains information about reports include files of reports
* transportation-module -  contains inforamtion about process transportation and the employees involved in this process

## Choosing storage for modules
### client-module, materials-module, cutter-module, envelope-module, order-module, packaging-module, printer-module, transportation-module
Relational db (Postgres, MySQL), because

* Relational data concerning the client will be stored
* The load on the database is small
* Binaries will not be stored

### preparation-document-module,report-module
s3 compatible storage(Minio), because

* binaries will be stored
* a large amount of data will be stored
