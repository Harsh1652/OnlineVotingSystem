
<?php 
    if(isset($_GET['added']))
    {
?>
        <div class="alert alert-success my-3" role="alert">
            Candidate has been added successfully.
        </div>
<?php 
    }else if(isset($_GET['delete_id']))
    {
        $d_id = $_GET['delete_id'];
        mysqli_query($db, "DELETE FROM candidate_details WHERE id = '". $d_id ."'") OR die(mysqli_error($db));
?>
       <div class="alert alert-danger my-3" role="alert">
            Candidate has been deleted successfully!
        </div>
<?php

    }
?>

<div class="row my-3">
    <div class="col-4">
        <h3>Add New Candidates</h3>
        <form method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <select class="form-control" name="election_id" required> 
                    <option value=""> Select Election </option>
                    <?php 
                        $fetchingElections = mysqli_query($db, "SELECT * FROM elections") OR die(mysqli_error($db));
                        $isAnyElectionAdded = mysqli_num_rows($fetchingElections);
                        if($isAnyElectionAdded > 0)
                        {
                            while($row = mysqli_fetch_assoc($fetchingElections))
                            {
                                $election_id = $row['id'];
                                $election_name = $row['election_topic'];
                                $allowed_candidates = $row['no_of_candidates'];

                                // Now checking how many candidates are added in this election 
                                $fetchingCandidate = mysqli_query($db, "SELECT * FROM candidate_details WHERE election_id = '". $election_id ."'") or die(mysqli_error($db));
                                $added_candidates = mysqli_num_rows($fetchingCandidate);

                                if($added_candidates < $allowed_candidates)
                                {
                        ?>
                                <option value="<?php echo $election_id; ?>"><?php echo $election_name; ?></option>
                        <?php
                                }
                            }
                        }else {
                    ?>
                            <option value=""> Please add election first </option>
                    <?php
                        }
                    ?>
                </select>
            </div>
            <div class="form-group">
                <input type="text" name="candidate_name" placeholder="Candidate Name" class="form-control" required />
            </div>
            
            <div class="form-group">
                <input type="text" name="candidate_details" placeholder="Candidate Details" class="form-control" required />
            </div>
            <input type="submit" value="Add Candidate" name="addCandidateBtn" class="btn btn-success" />
        </form>
    </div>   

    <div class="col-8">
        <h3>Candidate Details</h3>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">S.No</th>

                    <th scope="col">Name</th>
                    <th scope="col">Details</th>
                    <th scope="col">Election</th>
                    <th scope="col">Action </th>
                    
                </tr>
            </thead>
            <tbody>
                <?php 
                    $fetchingData = mysqli_query($db, "SELECT * FROM candidate_details") or die(mysqli_error($db)); 
                    $isAnyCandidateAdded = mysqli_num_rows($fetchingData);

                    if($isAnyCandidateAdded > 0)
                    {
                        $sno = 1;
                        while($row = mysqli_fetch_assoc($fetchingData))
                        {
                            $election_id = $row['election_id'];
                            $fetchingElectionName = mysqli_query($db, "SELECT * FROM elections WHERE id = '". $election_id ."'") or die(mysqli_error($db));
                            $execFetchingElectionNameQuery = mysqli_fetch_assoc($fetchingElectionName);
                            $election_name = $execFetchingElectionNameQuery['election_topic'];
                            $candidate_id = $row['id'];



                ?>
                            <tr>
                                <td><?php echo $sno++; ?></td>

                                <td><?php echo $row['candidate_name']; ?></td>
                                <td><?php echo $row['candidate_details']; ?></td>
                                <td><?php echo $election_name; ?></td>
                                <td> 
                                    <a href="#" class="btn btn-sm btn-warning"> Edit </a>
                                    <button class="btn btn-sm btn-danger" onclick="DeleteData(<?php echo $candidate_id; ?>)"> Delete </button>
                                </td>
                            </tr>   
                <?php
                        }
                    }else {
            ?>
                        <tr> 
                            <td colspan="5"> No any candidate is added yet. </td>
                        </tr>
            <?php
                    }
                ?>
            </tbody>    
        </table>
    </div>
</div>

<script>
    const DeleteData = (e_id) => 
    {
        let c = confirm("Are you really want to delete it?");

        if(c == true)
        {
            location.assign("index.php?addCandidatePage=1&delete_id=" + e_id);
        }
    }
</script>


<?php 

    if(isset($_POST['addCandidateBtn']))
    {
        $election_id = mysqli_real_escape_string($db, $_POST['election_id']);
        $candidate_name = mysqli_real_escape_string($db, $_POST['candidate_name']);
        $candidate_details = mysqli_real_escape_string($db, $_POST['candidate_details']);
        $inserted_by = $_SESSION['username'];
        $inserted_on = date("Y-m-d");

        
        
        // inserting into db
        mysqli_query($db, "INSERT INTO candidate_details(election_id, candidate_name, candidate_details, inserted_by, inserted_on) VALUES('". $election_id ."', '". $candidate_name ."', '". $candidate_details ."', '". $inserted_by ."', '". $inserted_on ."')") or die(mysqli_error($db));

        echo "<script> location.assign('index.php?addCandidatePage=1&added=1'); </script>";

        
    ?>
      <?php

    }






?>