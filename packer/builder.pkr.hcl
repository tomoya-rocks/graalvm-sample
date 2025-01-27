packer {
  required_plugins {
    amazon = {
      version = ">= 1.2.8"
      source  = "github.com/hashicorp/amazon"
    }
    ansible = {
      source  = "github.com/hashicorp/ansible"
      version = "~> 1"
    }
  }
}

variable "access_key" {
  type = string
  default = ""
}

variable "secret_key" {
  type = string
  default = ""
}

variable "region" {
  type = string
  default = ""
}

variable "source_ami" {
  type = string
  default = ""
}

variable "vpc_id" {
  type = string
  default = ""
}

variable "subnet_id" {
  type = string
  default = ""
}

source "amazon-ebs" "graalvm" {
  ami_name      = "graalvm-ami"
  instance_type = "t3.micro"
  access_key = "${var.access_key}"
  secret_key = "${var.secret_key}"
  region = "${var.region}"
  source_ami = "${var.source_ami}"
  ssh_username = "ec2-user"
  vpc_id = "${var.vpc_id}"
  subnet_id = "${var.subnet_id}"
  associate_public_ip_address = true
  launch_block_device_mappings {
    device_name = "/dev/sda1"
    volume_size = 40
    volume_type = "gp2"
  }
}

build {
  name    = "learn-packer"
  sources = [
    "source.amazon-ebs.graalvm"
  ]

  provisioner "ansible" {
    playbook_file = "../ansible/site.yml"
    extra_arguments = ["--scp-extra-args", "'-O'"]
  }
}