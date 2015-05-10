/**
 *
 * Created by sjyuan on 5/8/15.
 */
function checkFileExtName(){
    var twFile = document.getElementById("twFilePath").value;
    if(twFile.trim() == '' || twFile.substring(twFile.indexOf("."))!=".xlsx"){
        document.getElementById("errorMsg").style.display = "block";
        return false;
    }
    var telstraFile = document.getElementById("telstraFilePath").value;
    if(telstraFile.trim() == '' || telstraFile.substring(telstraFile.indexOf("."))!=".xlsx"){
        document.getElementById("errorMsg").style.display = "block";
        return false;
    }
    document.getElementById("errorMsg").style.display = "none";
    return true;
}
