package com.github.roberveral.dockerakka.http

import com.github.roberveral.dockerakka.cluster.master.ServiceMaster
import com.github.roberveral.dockerakka.cluster.master.ServiceMaster.ServiceList
import com.github.roberveral.dockerakka.cluster.worker.ServiceWorker
import com.github.roberveral.dockerakka.cluster.worker.ServiceWorker.TaskInfo
import com.github.roberveral.dockerakka.utils.DockerService
import spray.json._

/**
  * Definition of a ServiceDescription for the input request
  *
  * @param image     name of the image for the Docker service
  * @param ports     list of ports to bind in the Docker service
  * @param instances number of instances to create of the service
  * @author Rober Veral (roberveral@gmail.com)
  */
case class ServiceDescription(image: String, ports: List[Int], instances: Int) {
  require(instances > 0)
  require(image.nonEmpty)
}

/**
  * Definition of service instances for the input request
  *
  * @param instances new number of instances
  * @author Rober Veral (roberveral@gmail.com)
  */
case class ServiceInstances(instances: Int) {
  require(instances > 0)
}

/**
  * Describes a member of the cluster
  * @param address node address
  * @param roles node roles
  * @param status node status
  */
case class ClusterMember(address: String, roles: List[String], status: String)

/**
  * Trait with the implicit conversions from case classes to JSON
  * for marshalling and unmarshallig of requests.
  *
  * @author Rober Veral (roberveral@gmail.com)
  */
trait EventMarshalling extends DefaultJsonProtocol {
  implicit val serviceInstances: RootJsonFormat[ServiceInstances] = jsonFormat1(ServiceInstances)
  implicit val serviceDesc: RootJsonFormat[ServiceDescription] = jsonFormat3(ServiceDescription)
  implicit val serviceFormat: RootJsonFormat[DockerService] = jsonFormat3(DockerService)
  implicit val serviceListFormat: RootJsonFormat[ServiceList] = jsonFormat1(ServiceMaster.ServiceList)
  implicit val serviceResponse: RootJsonFormat[TaskInfo] = jsonFormat1(ServiceWorker.TaskInfo)
  implicit val memberResponse: RootJsonFormat[ClusterMember] = jsonFormat3(ClusterMember)
}
