package play.scalasupport

import java.lang.reflect.{Type, ParameterizedType}
import java.lang.annotation.Annotation

import play.data.binding._
import play.db.sql._

class PkBinder extends TypeBinder[Pk[_]] {

    override def bind(name:String, annotations:Array[Annotation], value:String, actualClass:Class[_], genericType:Type) = {
        actualClass match {
            case actualClass if actualClass == classOf[Pk[_]] => 
                value match {
                    case null => TODO
                    case v if v.trim() == "" => TODO
                    case v => 
                        try {
                            val parameterClass = genericType.asInstanceOf[ParameterizedType].getActualTypeArguments()(0)
                            val result = Binder.directBind(name, annotations, value, parameterClass.asInstanceOf[Class[_]])
                            Id(result)
                        } catch {
                            case e:Throwable => e.printStackTrace; TODO
                        }
                }                  
            case _ => null
        }
    }

}

class OptionBinder extends TypeBinder[Option[_]] {

    override def bind(name:String, annotations:Array[Annotation], value:String, actualClass:Class[_], genericType:Type) = {
        actualClass match {
            case actualClass if actualClass == classOf[Option[_]] => 
                   try {
                      val parameterClass = genericType.asInstanceOf[ParameterizedType].getActualTypeArguments()(0)
                      val result = Binder.directBind(name, annotations, value, parameterClass.asInstanceOf[Class[_]])
                      Some(result)
                   } catch {
                      case e:Throwable => e.printStackTrace; None
                   }
            case _ => null
        }
    }

}