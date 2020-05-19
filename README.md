# docxconverter

The project's goal is to create a Restfull Webservice to convert docx file to pdf. 

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
