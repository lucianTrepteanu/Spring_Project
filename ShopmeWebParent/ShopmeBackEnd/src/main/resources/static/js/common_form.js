$(document).ready(function(){
    $("#buttonCancel").on("click",function(){
        window.location=moduleURL;
    });

    $("#fileImage").change(function(){
        fileSize = this.files[0].size;
        alert("File size: " + fileSize);
        if(fileSize > 1048576){
            this.setCustomValidity("You must choose a smaller image");
            this.reportValidity();
        } else {
            this.setCustomValidity("");
            showImageThumbnail(this);
        }
    });
});

function showImageThumbnail(fileInput){
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    };
    reader.readAsDataURL(file);
}