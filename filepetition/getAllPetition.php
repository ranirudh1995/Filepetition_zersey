<?php 
 //Importing Database Script 
 require_once('dbConnect.php');
 
 //Creating sql query
 $sql = "SELECT * FROM petition";
 
 //getting result 
 $r = mysqli_query($con,$sql);
 
 //creating a blank array 
 $result = array();
 
 //looping through all the records fetched
 while($row = mysqli_fetch_array($r)){
 
 //Pushing name and id in the blank array created 
 array_push($result,array(
 "id"=>$row['id'],
"title"=>$row['title'],
"decision_maker"=>$row['decision_maker'],
"explanation"=>$row['expanation'],
"createdBy"=>$row['createdBy']
 ));
 }
 
 //Displaying the array in json format 
 echo json_encode(array('result'=>$result));
 
 mysqli_close($con);
?>
