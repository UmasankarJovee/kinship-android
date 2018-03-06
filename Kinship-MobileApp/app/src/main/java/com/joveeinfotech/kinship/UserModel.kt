package com.joveeinfotech.kinship

/**
 * Created by shanmugarajjoveeinfo on 3/3/18.
 */
class UserModel {
    var isSelected:Boolean?=null
    var languageNames:String?=null
    constructor(isSelected: Boolean,languageNames:String){
        this.isSelected=isSelected
        this.languageNames=languageNames
    }
    fun isSelected():Boolean{ return isSelected!! }
    fun setSelected(selected:Boolean){isSelected=selected}
    fun getlanguageNames():String{return languageNames!!}
    fun setlanguageNames(languageNames: String){this.languageNames=languageNames}
}