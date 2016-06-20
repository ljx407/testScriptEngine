importPackage(java.lang);

function getName(userModel) {
	System.out.println(userModel);
	var obj = eval('(' + userModel + ')');
	System.out.println(obj.username)
	System.out.println(obj.password)
	
}

function feeFun() {
	return "3.53";
}
