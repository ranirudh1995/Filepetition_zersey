<?php
 if($_SERVER['REQUEST_METHOD']=='POST'){

 //Getting values
 $firstname = $_POST['firstname'];
 $lastname = $_POST['lastname'];
 $email = $_POST['email'];
$password = $_POST['encrypted_password'];
$username = $_POST['username'];
echo $title;
echo $decision_maker;
 //Creating an sql query
 $sql = "INSERT INTO users (username,firstname,lastname,email,encrypted_password) VALUES ('$username','$firstname','$lastname','$email','$password')";

 //Importing our db connection script
 require_once('dbConnect.php');

 //Executing query to database
 if(mysqli_query($con,$sql)){
 echo 'User Added Successfully';
 }else{
 echo 'Could Not Add user';
 }

 //Closing the database 
 mysqli_close($con);
 }
?>
