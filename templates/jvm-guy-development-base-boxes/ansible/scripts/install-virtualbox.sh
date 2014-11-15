#!/bin/sh -x

# Making sure everything is updated
apt-get update
apt-get -y upgrade
apt-get -f install 

# Install the VirtualBox guest additions
VBOX_VERSION=$(cat /home/vagrant/.vbox_version)
VBOX_ISO=VBoxGuestAdditions_$VBOX_VERSION.iso
mount -o loop $VBOX_ISO /mnt
yes | sh /mnt/VBoxLinuxAdditions.run
umount /mnt

#Remove the guest additions ISO so it doesn't take up space in the finished base box:
rm -rf /home/vagrant/VBoxGuestAdditions_*.iso
