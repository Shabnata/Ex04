function studentIDFill(textBox){
	if(textBox === null){
		return;
	}

	var idMinLength = 9;
	var boxText = textBox.value;

	while(boxText.length < idMinLength){
		boxText = "0" + boxText;
	}

	textBox.value = boxText;
}

function bookISBNFill(textBox){
	if(textBox === null){
		return;
	}
	// Wikipedia states that ISBN's are 13 digits long
	// https://en.wikipedia.org/wiki/International_Standard_Book_Number
	var ISBNMinLength = 13;
	var boxText = textBox.value;

	while(boxText.length < ISBNMinLength){
		boxText = "0" + boxText;
	}

	textBox.value = boxText;
}

function focusClearIDTextBox(textBox){
	var idMinLength = 9;
	if(textBox === null || textBox.value.length >= idMinLength){
		return;
	}

	textBox.value = "";
}

function focusClearISBNTextBox(textBox){
	var ISBNMinLength = 13;
	if(textBox === null || textBox.value.length >= ISBNMinLength){
		return;
	}

	textBox.value = "";
}