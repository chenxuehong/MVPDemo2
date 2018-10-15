package com.common.mvpdemo2.model

data class PersonalCenterModel(
        val HeadUrl: String = "",
        val Name: String = "",
        val RoleName: String = "",
        val RecommandName: String = "",
        val RegionName: String = "",
        val IsNeedRecharge: Boolean = false,
        val Balance: Double = 0.00,
        val BalanceStr: String = "0.00",
        val isOpenEnsure: Boolean = false,
        val EnsureMoney: Double = 0.00,
        val EnsureMoneyStr: String = "0.00",
        val IsAnnouncement: Boolean = false,
        val Announcement: String = "",
        val IsMoreAddress: Boolean = false,
        val MemberAddressList: MutableList<MemberAddress> = mutableListOf(),
        val PendingOrderCount: String = "",
        val MemberCheckCount: String = "",
        val BalanceCheckCount: String = "",
        val DataInfoList: MutableList<DataInfo> = mutableListOf(),
        val TeamNumber: String = "",
        val AuthCount: String = "",
        val NoAuthCount: String = "",
        val MessageCount: String = "",
        val MemberCountList: MutableList<MemberCount> = mutableListOf(),
        val LowMemberSellList: MutableList<LowMemberSell> = mutableListOf(),
        val IsTriggerRedLine: Boolean = false,
        val RedMoney: Double = 0.00,
        val RedMoneyStr: String = "0.00",
        val RedRemark: String = "",
        val IsLimint: String = ""
)

data class LowMemberSell(
        val LowHeadUrl: String = "",
        val LowName: String = "",
        val LowRoleName: String = "",
        val LowShipmentMoney: Double = 0.00,
        val LowShipmentMoneyStr: String = "0.00"
)

data class DataInfo(
        val NewAgent: String = "",
        val ExportMoney: Double = 0.00,
        val ExportMoneyStr: String = "0.00",
        val Earn: Double = 0.00,
        val EarnStr: String = "0.00",
        val Type: String = "1"
)

data class MemberAddress(
        val Id: String = "",
        val Name: String = "",
        val Phone: String = "",
        val IsDefault: Boolean = false,
        val Delstatus: Boolean = false,
        val Province: String = "",
        val City: String = "",
        val Area: String = "",
        val Address: String = ""
)

data class MemberCount(
        val RoleName: String = "",
        val Num: String = ""
)

data class JokeModel(
    val data: MutableList<Data>?= mutableListOf()
){
    data class Data(
            val content: String,
            val hashId: String,
            val unixtime: Int,
            val updatetime: String
    )
}
