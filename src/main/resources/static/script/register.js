var login = $("#login");
var email = $("#email");
var password = $("#password");
var cpassword = $("#confirm_password");
var usernameRegex = /^[a-zA-Z0-9]{5,20}$/;
var emailRegex = /^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/;

/*This regular expression expects atleast 1 small-case letter, 1 Capital letter, 
1 digit and the length should be between 6-10 characters.
The sequence of the characters is not important.*/
var passwordRegex = /(?=^.{6,15}$)(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/;

function checkLogin() {
    if($("#b-helper-login").length){
        $("#b-helper-login").hide();
    }
    var vlogin = login.val();

    if (usernameRegex.test(vlogin)) {
        login.removeClass("invalid");
        login.addClass("valid");
        $("#helper-login").hide();
        checkform();
        return true;
    } else {
        if (vlogin.length < 5 || vlogin.length > 20) {
            $("#helper-login").attr("data-error", "Login must be at least 5 and up to 20 characters long.").show(500);
        } else {
            $("#helper-login").attr("data-error", "Invalid character in login").show(500);
        }
        login.addClass("invalid");
        login.removeClass("valid");
    }
    checkform();
    return false;

};

function checkEmail() {
    if($("#b-helper-email").length){
        $("#b-helper-email").hide();
    }
    var vemail = email.val();

    if (emailRegex.test(vemail)) {
        email.removeClass("invalid");
        email.addClass("valid");
        $("#helper-email").hide();
        checkform();
        return true;
    } else {
        $("#helper-email").attr("data-error", "Email is invalid").show();
        email.addClass("invalid");
        email.removeClass("valid");
    }
    checkform();
    return false;

};

function checkCPassword() {
    if($("#b-helper-cpassword").length){
        $("#b-helper-cpassword").hide();
    }
    var vcpassword = cpassword.val();
    var vpassword = password.val();

    if (vpassword === vcpassword) {
        $("#helper-cpassword").hide();
        cpassword.removeClass("invalid");
        cpassword.addClass("valid");
        checkform();
        return true;
    } else {
        $("#helper-cpassword").attr("data-error", "Passwords do not match.").show();
        cpassword.addClass("invalid");
        cpassword.removeClass("valid");
    }
    checkform();
    return false;
}

function checkPassword() {
    if($("#b-helper-password").length){
        $("#b-helper-password").hide();
    }
    var vpassword = password.val();
    var vcpassword = cpassword.val();
    if (vcpassword.length != 0) {
        checkCPassword();
    }
    if (passwordRegex.test(vpassword)) {
        password.removeClass("invalid");
        password.addClass("valid");
        $("#helper-password").hide();
        checkform();
        return true;
    } else {
        if (vpassword.length < 6 || vpassword.length > 10) {
            $("#helper-password").attr("data-error", "Length of the password should be between 6-10 characters.").show();
        } else {
            $("#helper-password").attr("data-error", "Password must have at least 1 small-case letter, 1 capital letter and 1 digit.").show();
        }
        password.addClass("invalid");
        password.removeClass("valid");
        checkform();
    }
    return false;
};

function checkform() {
    if (login.hasClass("valid") &&
        email.hasClass("valid") &&
        password.hasClass("valid") &&
        cpassword.hasClass("valid")) {
        $("#button-register").attr("disabled", false);
        $("#button-register").removeAttr("disabled");
        return true;
    } else {
        $("#button-register").attr("disabled", true);
    }
    return false;
};

$(document).ready(function () {
    if($("#b-helper-login").length){
        login.addClass("invalid");;
    }
    if($("#b-helper-email").length){
        email.addClass("invalid");
    }
    if($("#b-helper-password").length){
        password.addClass("invalid");
    }
    if($("#b-helper-cpassword").length){
        cpassword.addClass("invalid");
    }
});

login.keyup(checkLogin);
login.change(checkLogin);
email.keyup(checkEmail);
email.change(checkEmail);
cpassword.keyup(checkCPassword);
cpassword.change(checkCPassword);
password.keyup(checkPassword);
password.change(checkPassword);
