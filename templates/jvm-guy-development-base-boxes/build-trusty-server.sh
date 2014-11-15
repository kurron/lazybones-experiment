#!/bin/bash

export PACKER_LOG=activate
#export PACKER_LOG_PATH=/tmp

packer validate trusty-x64-server.json
packer build trusty-x64-server.json
vagrant box add --force --name packer/trusty-x64-server vagrant/trusty-x64-server.box
vagrant box list
