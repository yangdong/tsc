/**
 *
 * Created by sjyuan on 5/8/15.
 */
function checkFileExtName() {

    var twFile = document.getElementById("twFilePath").value;
    var extName = twFile.substring(twFile.lastIndexOf("."));
    if (twFile.trim() == '' || extName != ".xlsx" && extName != ".xls") {
        document.getElementById("errorMsg").style.display = "block";
        return false;
    }
    var telstraFile = document.getElementById("telstraFilePath").value;

    extName = telstraFile.substring(telstraFile.lastIndexOf("."));
    if (telstraFile.trim() == '' || extName != ".xlsx" && extName != ".xls") {
        document.getElementById("errorMsg").style.display = "block";
        return false;
    }
    document.getElementById("errorMsg").style.display = "none";
    return true;
}
