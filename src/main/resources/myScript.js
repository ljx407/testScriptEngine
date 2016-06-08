importPackage(java.lang);
function getName(name) {
	if ((name != "") && (name != null)) {
		System.out.println("Hello " + name + "!")
	} else {
		System.out.println("Hello!")
	}
}
getName("TOM");