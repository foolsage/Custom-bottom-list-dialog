# Custom bottom list dialog

##### Add in your root build.gradle at the end of repositories :

    maven { url 'https://jitpack.io' }

##### Add the dependency :

    implementation 'com.google.android.material:material:1.2.0-alpha02'
    implementation 'com.github.foolsage:Custom-bottom-list-dialog:1.0.0'


### Usage
##### New :
    customBottomDialog = new CustomBottomDialog(this, stringList);
##### Set listener :
    customBottomDialog.setOnListItemClickedListener(this);
##### Set button text :
    customBottomDialog.setBottomText("Cancel");
##### Set button text bold :
    customBottomDialog.setBottomTextBold(false);
##### Set button text color :
    customBottomDialog.setBottomTextColor(getResources().getColor(R.color.colorBlue));
or

    customBottomDialog.setBottomTextColor(Color.BLUE);
##### Show :
    customBottomDialog.show();
