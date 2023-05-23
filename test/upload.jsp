<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Upload</title>
  </head>
  <body>
    <h1>Upload file</h1>
    <form action="./upload.do" method="post" enctype="multipart/form-data">
      <input type="file" name="cv" />
      <input type="submit" value="Valider" />
    </form>
  </body>
</html>
