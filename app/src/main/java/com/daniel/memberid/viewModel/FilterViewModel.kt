package com.daniel.memberid.viewModel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daniel.memberid.CommonsCons
import java.text.NumberFormat
import java.util.*

class FilterViewModel(application: Application) : AndroidViewModel(application) {

    val currentPoinString : ObservableField<String> =  ObservableField("10.000")
    val currentPoinStringRange : ObservableField<String> =  ObservableField("10000-10000")
    val currentType : ObservableField<String> = ObservableField("")
    val allTypeChecked : ObservableBoolean = ObservableBoolean(false)
    val voucherChecked : ObservableBoolean = ObservableBoolean(false)
    val productsChecked : ObservableBoolean = ObservableBoolean(false)
    val othersChecked : ObservableBoolean = ObservableBoolean(false)
    val isTypeVisible : ObservableBoolean = ObservableBoolean(false)
    val isFilterExist : ObservableBoolean = ObservableBoolean(false)
    val isPoinVisible: ObservableBoolean = ObservableBoolean(false)
    val isPoinChanged: MutableLiveData<Boolean> = MutableLiveData()
    val resetsPoin : MutableLiveData<Boolean> = MutableLiveData()
    val filtered : MutableLiveData<Boolean> = MutableLiveData()
    val currentTypeArrayString = emptyArray<String>().toMutableList()
    private var allTypeTrigger: Boolean = false
    private var cancelTypeTrigger :Boolean =false
    var currentPoin : Int =  0
    var feedTrigger : Boolean = false


    init {
        filtered.value = false
        resetsPoin.value = false
        isPoinChanged.value = false
    }

    fun resetPoin(){
        resetsPoin.value = true
        isPoinVisible.set(false)
    }

    fun setPoinChanged(i:Int){

        val poin = i*10000
        currentPoinString.set(NumberFormat.getNumberInstance(Locale.GERMAN).format(poin))
        currentPoinStringRange.set("Poin 10000 - $poin")
        currentPoin = poin
        if(i>1){
            isFilterExist.set(true)
            isPoinVisible.set(true)
        }
    }

    fun allTypeChanged(){

        allTypeTrigger = true
       if(!cancelTypeTrigger){
           if(!allTypeChecked.get()){
               voucherChecked.set(true)
               productsChecked.set(true)
               othersChecked.set(true)
           }
           else {
               voucherChecked.set(false)
               productsChecked.set(false)
               othersChecked.set(false)
           }
       }
        cancelTypeTrigger = false
    }
    fun voucherChanged(){
       if(allTypeTrigger || cancelTypeTrigger || feedTrigger) {
           if (voucherChecked.get()) {
               currentTypeArrayString.add(CommonsCons.AWARD_TYPE_VOUCHERS)
           } else {
               currentTypeArrayString.remove(CommonsCons.AWARD_TYPE_VOUCHERS)
           }
       }  else {
           if (!voucherChecked.get()) {
               currentTypeArrayString.add(CommonsCons.AWARD_TYPE_VOUCHERS)
           } else {
               currentTypeArrayString.remove(CommonsCons.AWARD_TYPE_VOUCHERS)
           }
       }
        allTypeTrigger =false
        feedTrigger = false
        setCurrentType()
    }
    fun productsChanged(){

        if(allTypeTrigger || cancelTypeTrigger || feedTrigger) {
            if (productsChecked.get()) {
                currentTypeArrayString.add(CommonsCons.AWARD_TYPE_PRODUCTS)
            } else {
                currentTypeArrayString.remove(CommonsCons.AWARD_TYPE_PRODUCTS)
            }
        } else {
            if (!productsChecked.get()) {
                currentTypeArrayString.add(CommonsCons.AWARD_TYPE_PRODUCTS)
            } else {
                currentTypeArrayString.remove(CommonsCons.AWARD_TYPE_PRODUCTS)
            }
        }
        setCurrentType()
    }
    fun othersChanged(){

        if(allTypeTrigger || cancelTypeTrigger || feedTrigger) {
            if (othersChecked.get()) {
                currentTypeArrayString.add(CommonsCons.AWARD_TYPE_GIFTCARDS)
            } else {
                currentTypeArrayString.remove(CommonsCons.AWARD_TYPE_GIFTCARDS)
            }
        } else {
            if (!othersChecked.get()) {
                currentTypeArrayString.add(CommonsCons.AWARD_TYPE_GIFTCARDS)
            } else {
                currentTypeArrayString.remove(CommonsCons.AWARD_TYPE_GIFTCARDS)
            }
        }
        setCurrentType()
    }

    fun cancelType(){
        allTypeChecked.set(false)
        voucherChecked.set(false)
        productsChecked.set(false)
        othersChecked.set(false)
        cancelTypeTrigger =true

    }

    fun filter() {
        filtered.value = true
    }

    fun clearFilter(){
       if(currentTypeArrayString.count()>0){
           cancelType()
           resetPoin()
       } else {
           resetPoin()
       }
        isFilterExist.set(false)
    }

    fun setPoinAndType(poin:Int?, type: Array<String>?){
        if(poin!=null && poin>10000){
            currentPoin = poin
            isPoinChanged.value = true
        }
        if(type!=null && type.count()>0){
            type.forEach {
                when (it) {
                    CommonsCons.AWARD_TYPE_VOUCHERS -> voucherChecked.set(true)
                    CommonsCons.AWARD_TYPE_GIFTCARDS -> othersChecked.set(true)
                    CommonsCons.AWARD_TYPE_PRODUCTS -> productsChecked.set(true)
                }
            }
            if(type.count()>=3){
                allTypeChecked.set(true)
            }
            feedTrigger = true
        }
    }


    private fun setCurrentType(){
            currentType.set(currentTypeArrayString.joinToString())

        isFilterExist.set(currentTypeArrayString.count()>0 || currentPoin>10000)
        isTypeVisible.set(currentTypeArrayString.count()>0)
        cancelTypeTrigger = false


    }

}