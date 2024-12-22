<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page PHP dynamique</title>
</head>

<body>
    <?php for ($i = 1; $i < 4; $i++) { ?>
        <?php echo "<h$i><u>Title $i</u></h$i>"; ?>
        <img src="assets/image.jpg" alt="<?php echo "image $i"; ?>" style="width:<?php echo $i * 100; ?>px;">
    <?php } ?>
</body>

</html>