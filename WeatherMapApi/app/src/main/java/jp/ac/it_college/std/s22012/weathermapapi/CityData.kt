package jp.ac.it_college.std.s22012.weathermapapi


data class CityData(
    val locationId: Int,
    val name: String
)

object CityDataList {
    val cityList = listOf(
        CityData(2130037, "北海道(札幌)"),
        CityData(2130658, "青森"),
        CityData(2111834, "岩手(盛岡)"),
        CityData(2111149, "宮城(仙台)"),
        CityData(2113719, "秋田"),
        CityData(2110556, "山形"),
        CityData(2112923, "福島"),
        CityData(2111901, "茨城(水戸)"),
        CityData(1849053, "栃木(宇都宮)"),
        CityData(1857843, "群馬(前橋)"),
        CityData(1853226, "埼玉(さいたま)"),
        CityData(2113014, "千葉"),
        CityData(1850144, "東京"),
        CityData(1848354, "神奈川(横浜)"),
        CityData(1855431, "新潟"),
        CityData(1849876, "富山"),
        CityData(1860243, "石川"),
        CityData(1863985, "福井"),
        CityData(1859100, "山梨(甲府)"),
        CityData(1856215, "長野"),
        CityData(1863641, "岐阜"),
        CityData(1851717, "静岡"),
        CityData(1865694, "愛知(名古屋)"),
        CityData(1849796, "三重(津)"),
        CityData(1853574, "滋賀(大津)"),
        CityData(1857910, "京都"),
        CityData(1853909, "大阪"),
        CityData(1859171, "兵庫"),
        CityData(1855612, "奈良"),
        CityData(1926004, "和歌山"),
        CityData(1849892, "鳥取"),
        CityData(1857550, "島根(松江)"),
        CityData(1854383, "岡山"),
        CityData(1862415, "広島"),
        CityData(1848689, "山口"),
        CityData(1850158, "徳島"),
        CityData(1851100, "香川(高松)"),
        CityData(1926099, "愛媛(松山)"),
        CityData(1859146, "高知"),
        CityData(1863967, "福岡"),
        CityData(1853303, "佐賀"),
        CityData(1856177, "長崎"),
        CityData(1858421, "熊本"),
        CityData(1854487, "大分"),
        CityData(1856717, "宮崎"),
        CityData(1860827, "鹿児島"),
        CityData(1856035, "沖縄(那覇)")
    )

    fun getLocationId(cityName: String): Int? {
        return cityList.find { it.name == cityName }?.locationId
    }
}






