<?php
  $field1 = $_POST["id"];
  $field2 = $_POST["firstName"];
  $field3 = $_POST["lastName"];
  $field4 = $_POST["dateofbirth"];
  $output = shell_exec("./createPatient.sh '$field1' '$field2' '$field3' '$field4' ");
  echo $output;
?>