#!/bin/sh

do_expand_rootfs() {
  ROOT_PART=$(mount | sed -n 's|^/dev/\(.*\) on / .*|\1|p')
  BOOT_PATH=`mount | grep "on / type ext4" | awk '{print $1}'`
  if [ "$BOOT_PATH" = "/dev/mmcblk1p2" ] ; then
        MMCBLK="mmcblk1"
        PART_NUM=${ROOT_PART#mmcblk1p}
  else
        MMCBLK="mmcblk0"
        PART_NUM=${ROOT_PART#mmcblk0p}
  fi

  if [ "$PART_NUM" = "$ROOT_PART" ]; then
    echo "$ROOT_PART is not an SD card. Don't know how to expand"
    return 0
  fi

  # NOTE: the NOOBS partition layout confuses parted. For now, let's only 
  # agree to work with a sufficiently simple partition layout
  if [ "$PART_NUM" -ne 2 ]; then
    echo "Your partition layout is not currently supported by this tool."
    return 0
  fi

  #LAST_PART_NUM=$(parted /dev/$MMCBLK -ms unit s p | tail -n 1 | cut -f 1 -d:)
  if [ "$BOOT_PATH" == "/dev/mmcblk1p2" ] ; then
      LAST_PART_NUM=`ls /dev/mmcblk1p* | wc -l`
  else
      LAST_PART_NUM=`ls /dev/mmcblk0p* | wc -l`
  fi

  
  echo "LAST_PART_NUM=$LAST_PART_NUM"
 
  if [ $LAST_PART_NUM -ne $PART_NUM ]; then
    echo "$ROOT_PART is not the last partition. Don't know how to expand"
    return 0
  fi

  # Get the starting offset of the root partition
  #PART_START=$(parted /dev/$MMCBLK -ms unit s p | grep "^${PART_NUM}" | cut -f 2 -d: | sed 's/[^0-9]//g')
  PART_START=`fdisk -l /dev/$MMCBLK | grep $ROOT_PART | awk '{print $2}'`

  [ "$PART_START" ] || return 1
  # Return value will likely be error for fdisk as it fails to reload the
  # partition table because the root fs is mounted

  fdisk /dev/$MMCBLK <<EOF
p
d
$PART_NUM
n
p
$PART_NUM
$PART_START

p
w
EOF

resize2fs /dev/$ROOT_PART
sync

    echo "Root partition has been resized.\nThe filesystem will be enlarged upon the next reboot"
}

do_expand_rootfs
