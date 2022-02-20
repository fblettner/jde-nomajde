# JAVA API for JDEDWARDS
### Built with VSCode and JDK 1.8

## Functionalities
- jdebip : export XML source or PDF Output from JD Edwards BLOB columns
- tools : encode / decode a password to update encrypt password into config files

## JDEBIP
This class can be used to export blob column for BI Publisher from JD Edwards Database. This class can be extended to export BLOB for all tables.

| Parameter     | Description                       |
| ---           | ---                               |
| URL   |   JDBC String for database connection |
| USER  | User to login into the database |
| PASSWORD | Password to login into the database |
| OutputDirectory | Output directory for files exported from blob field |
| XML   | SQL Query to get XML source |
| PDF   | SQL Query to get PDF output |
| REMOVE_RD | If you need to retrieve batch from PrintQueue, record should be deleted |
| DELETE_F9563110 | SQL query to delete record into F9563110 |
| DELETE_F95630 | SQL query to delete record into F95630 |

## Usage
- Extract XML Source
java -cp ../dist/nomajde.jar jdebip XML \<OBJECT_NAME> \<VERSION> \<LANGUAGE> \<JOB_NUMBER>
 Extract PDF output
java -cp ../dist/nomajde.jar jdebip PDF \<OBJECT_NAME> \<VERSION> \<LANGUAGE> \<JOB_NUMBER>
