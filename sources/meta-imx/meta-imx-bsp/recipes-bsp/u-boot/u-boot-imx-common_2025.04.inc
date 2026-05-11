DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "${UBOOT_SRC};branch=${SRCBRANCH}"

### NXP github
#UBOOT_SRC ?= "git://github.com/nxp-imx/uboot-imx.git;protocol=https"
#SRCBRANCH = "lf_v2025.04"
#LOCALVERSION ?= "-${SRCBRANCH}"
#SRCREV = "4ddbad60eff308a5b356fb9ab8734ac382ddd692"

### Debix github (John_gao)
UBOOT_SRC ?= "git://github.com/debix-tech/uboot-nxp-debix;protocol=https"
SRCBRANCH = "lf_v2025.04-yocto-L6.12.49-2.2.0-debix-bmb-13-a1"
LOCALVERSION ?= "-${SRCBRANCH}"
SRCREV = "4cf35bcdbb763d617f659be7caee0fc44d1ddc12"

DEPENDS += " \
    bc-native \
    bison-native \
    dtc-native \
    flex-native \
    gnutls-native \
    python3-setuptools-native \
    xxd-native \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion

BOOT_TOOLS = "imx-boot-tools"
