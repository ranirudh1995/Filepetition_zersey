<?php 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 //Getting values
 $title = trim($_POST['title']);
 $decision_maker = $_POST['decision_maker'];
 $explanation = trim($_POST['explanation']);
$createdBy = $_POST['createdBy'];
echo $title;
echo $decision_maker; 
 //Creating an sql query
 $sql = "INSERT INTO petition (title,decision_maker,explanation,createdBy) VALUES ('$title','$decision_maker','$explanation','$$createdBy')";
 
 //Importing our db connection script
 require_once('dbConnect.php');
 
 //Executing query to database
 if(mysqli_query($con,$sql)){
 echo 'Petition Added Successfully';
 }else{
 echo 'Could Not Add petition';
 }
 
 //Closing the database 
 mysqli_close($con);
 }
?>
