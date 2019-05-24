<?php
    $output = shell_exec("./loadPatients.sh");
    $json = json_decode($output);
    echo $json;
?>