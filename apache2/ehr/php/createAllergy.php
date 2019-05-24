<?php
  $field1 = $_POST["patientId"];
  $field2 = $_POST["allergyName"];
  $output1 = shell_exec("./createAllergy.sh '$field1' '$field2' ");
?>