package com.daniel.memberid.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daniel.memberid.CommonsCons
import com.daniel.memberid.model.AwardsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedPageViewModel(application: Application) : AndroidViewModel(application) {

    val awardList = MutableLiveData<MutableList<AwardsModel>>()
    var awardListFiltered = MutableLiveData<MutableList<AwardsModel>>()
    var logout = MutableLiveData<Boolean>()

    init {
        logout.value = false
        awardList.value = emptyList<AwardsModel>().toMutableList()
    }

    suspend fun addAward(typeArray: MutableList<String>?, poin: Int?) :List<AwardsModel>{
       return withContext(Dispatchers.IO){
           var awards =  emptyList<AwardsModel>().toMutableList()
           awards.add(
               AwardsModel(CommonsCons.AWARD_TYPE_GIFTCARDS, 50000, "GIFT CARD IDR 500.000")
           )
           awards.add(
               AwardsModel(CommonsCons.AWARD_TYPE_GIFTCARDS, 200000, "GIFT CARD IDR 2.000.000")
           )
           awards.add(
               AwardsModel(CommonsCons.AWARD_TYPE_PRODUCTS, 700000, "iphone 5")
           )
           awards.add(
               AwardsModel(CommonsCons.AWARD_TYPE_PRODUCTS, 1000000, "iphone 8 replica")
           )
           awards.add(
               AwardsModel(CommonsCons.AWARD_TYPE_VOUCHERS, 800000, "Discount 50% Iphone")
           )
           awards.add(
               AwardsModel(CommonsCons.AWARD_TYPE_VOUCHERS, 300000, "Discount 30% Samsung")
           )
           awards.add(
               AwardsModel(CommonsCons.AWARD_TYPE_VOUCHERS, 500000, "Discount 70% Samsung")
           )
           if(poin!=null && poin>10000 ){
               awards = awards.filter { it.poinNeeded<=poin}.toMutableList()
               awardList.value?.addAll(awards)
           }
           if(typeArray!=null && typeArray.count()>0 ){
               awards = awards.filter { typeArray.contains(it.awardsType)}.toMutableList()
               awardList.value?.addAll(awards)

           }
           awardList.value?.addAll(awards)
           awards
       }
    }

    fun getAwards(){

        val awards =  emptyList<AwardsModel>().toMutableList()

        awards.add(
           AwardsModel(CommonsCons.AWARD_TYPE_GIFTCARDS, 500000, "GIFT CARD IDR 1.000.000")
       )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_GIFTCARDS, 100000, "GIFT CARD IDR 1.000.000")
        )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_GIFTCARDS, 300000, "GIFT CARD IDR 3.000.000")
        )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_PRODUCTS, 50000, "CAKE FASHON")
        )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_PRODUCTS, 700000, "iphone 5")
        )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_PRODUCTS, 1000000, "iphone 8 replica")
        )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_VOUCHERS, 800000, "Discount 50% Iphone")
        )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_VOUCHERS, 300000, "Discount 30% Samsung")
        )
        awards.add(
            AwardsModel(CommonsCons.AWARD_TYPE_VOUCHERS, 500000, "Discount 70% Samsung")
        )

        awardList.value?.addAll(awards)
    }

    fun logout(){
        logout.value = true
    }

    fun setFilter(poin:Int?, filterType:MutableList<String>?){
        if(poin!=null && poin>10000){
            awardList.value = awardList.value?.filter { it.poinNeeded<=poin }?.toMutableList()
            awardListFiltered = awardList
        }
        if(filterType!=null && filterType.count()>0){
            awardList.value =  awardList.value?.filter { filterType.contains(it.awardsType) }?.toMutableList()
            awardListFiltered = awardList
        }
        if(filterType?.count()==0 && poin==10000){
            awardListFiltered = awardList
        }
    }

}