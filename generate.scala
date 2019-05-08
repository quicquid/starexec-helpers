import ammonite.ops._

object Gen extends App {
  def script(command : String, options : Map[String,String], timeout : Int) = {
    val optstr=options.toList.map({ case (o,v) => s"$o=$v" }).mkString(":")
    val scr = s"""
#!/bin/sh

${command} --input_syntax smtlib2 --mode smtcomp  -t ${timeout} --forced_options ${optstr} $$1
"""
    scr
  }


  val options = Map(
    "thi" -> List("off","new"),
    "thia" -> List("off","lambda","lambda_merge"), //add store later
    "exarr" -> List("off","const","all") //add dynamic later
  )

  def addAll[U,V](map : Map[U,V], key : U, list : List[V]) = {
    list.map(x => map + ((key,x)) )
  }

  def optionString(m: Map[String,String]) = m.toList.map({ case (k,v) => s"$k-$v" }).mkString("--")

  def apply() = {
    var opts = List(Map[String,String]())
    options.keySet.map( key => {
      val list = options(key)
      opts = opts.flatMap( map => addAll(map, key, list) )
    })

    opts.map(x => {
      println(optionString(x))
      write(pwd/"bin"/s"starexec_run_vampire_${optionString(x)}", script("$(dirname \"$0\")/vampire",x,120) )
    })

    //print(script("bin/vampire", Map("thi"->"new", "thia"->"lambda"), 120))
  }

  apply()

}

