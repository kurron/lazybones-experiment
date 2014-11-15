#!/bin/bash

export PACKER_LOG=activate
#export PACKER_LOG_PATH=/tmp

packer validate trusty-x64-desktop.json
packer build trusty-x64-desktop.json
vagrant box add --force --name packer/trusty-x64-desktop vagrant/trusty-x64-desktop.box
vagrant box list
