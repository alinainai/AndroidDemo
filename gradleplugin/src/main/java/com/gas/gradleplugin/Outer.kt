import org.gradle.api.Action

open class Outer {
    var name: String? = ""
    var inner = Inner()
    fun inner(innerAct: Action<Inner>){
        innerAct.execute(inner)
    }
}
open class Inner {
    var name: String? = ""
}