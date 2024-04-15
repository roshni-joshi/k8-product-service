provider "google" {
  project = "csci5409-k8-417419"
  region = "northamerica-northeast1"
}

resource "google_container_cluster" "csci5409-k8-cluster" {
  name = "csci5409-k8-cluster"
  location = "northamerica-northeast1-c"
  initial_node_count = 1
  
  node_config {
    machine_type = "e2-micro"
    disk_size_gb = 10
    disk_type = "pd-standard"
    image_type = "COS_CONTAINERD"
  }
}