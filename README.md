# docxconverter

The project's goal is to create a Restfull Webservice to convert docx file to pdf.

It is a spring boot (V 2.2.6) project developed with kotlin  and IntelliJ as IDE. It was 
necessary  to use external libraries such  as apache.poi for document convertion  and apache.common.io
to extract documents's metadatas.

The Api can be called from localhost at the port 8080 and provides 5 endpoints whose can be tested with postman:

- service/storage/add:
 receives as parameter the document which is to be managed by the service,
in the form of a multipart file, the file contains metada which are stored in the database

- service/storage/getPdf/{id}:
 creates a PDF file from the document whose ID was passed with
and returns the PDF as return value.

- service/storage/storeConvert: stores the source documen and immediately creates a PDF file and delivers it 
together with the ID of the metadata record in the header of the corresponding.

- service/storage/sourceDoc/{id}:
 returns the original document, whose ID was passed.

- service/storage/deleteDoc/{id}:
 deletes both the original document(whose ID was passed ) from the FileStore and the Metadata from the database 

# Usage

To start the project it is necessary to create a postgres database first.
One of the easier way to do it is to run a Docker command

### init

```docker
docker run --name some-postgres -p5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=document_DB -d postgres
```

# License

      Copyright (c) 2020 spiritdev Softwareentwicklung GmbH. All rights reserved.

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
