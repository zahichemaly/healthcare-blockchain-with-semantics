<?php
  $field1 = $_POST["prescPatientId"];
  $field2 = $_POST["drugName"];
  $field3 = $_POST["dosage"];
  $output = shell_exec("./createPrescription.sh '$field1' '$field2' '$field3' ");
  echo $output;
?>