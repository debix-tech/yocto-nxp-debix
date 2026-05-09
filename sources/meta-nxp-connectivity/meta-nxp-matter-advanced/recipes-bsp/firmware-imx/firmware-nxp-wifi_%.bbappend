# Use the latest revision

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ca53281cc0caa7e320d4945a896fb837"

IMX_FIRMWARE_SRC ?= "git://github.com/nxp-imx/imx-firmware.git;protocol=https"
SRC_URI = "${IMX_FIRMWARE_SRC};branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.6.52_2.2.0"
SRCREV = "2978f3c88d6bcc5695a7b45f1936f18d31eebfa8"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://IW612-Q3-25-RC2-p26.7/sduart_nw61x_v1.bin.se"
SRC_URI += "file://IW612-Q3-25-RC2-p26.7/sd_w61x_v1.bin.se"
SRC_URI += "file://IW612-Q3-25-RC2-p26.7/uartspi_n61x_v1.bin.se"

SRC_URI += "file://IW610-Q3-25-RC2-p73/sd_iw610.bin.se"
SRC_URI += "file://IW610-Q3-25-RC2-p73/sduart_iw610.bin.se"
SRC_URI += "file://IW610-Q3-25-RC2-p73/uart_iw610_bt.bin.se"
SRC_URI += "file://IW610-Q3-25-RC2-p73/uartspi_iw610.bin.se"
SRC_URI += "file://IW610-Q3-25-RC2-p73/sduartspi_iw610.bin.se"
SRC_URI += "file://0001-Add-IW610-15.4-firmware-calibration-file-and-use-it-.patch"

do_install:prepend() {
    rm -f ${S}/nxp/FwImage_IW612_SD/*.se
    cp ${UNPACKDIR}/IW612-Q3-25-RC2-p26.7/sduart_nw61x_v1.bin.se ${S}/nxp/FwImage_IW612_SD
    cp ${UNPACKDIR}/IW612-Q3-25-RC2-p26.7/sd_w61x_v1.bin.se ${S}/nxp/FwImage_IW612_SD
    cp ${UNPACKDIR}/IW612-Q3-25-RC2-p26.7/uartspi_n61x_v1.bin.se ${S}/nxp/FwImage_IW612_SD

    rm -f ${S}/nxp/FwImage_IW610_SD/*.se
    cp ${UNPACKDIR}/IW610-Q3-25-RC2-p73/sd_iw610.bin.se ${S}/nxp/FwImage_IW610_SD
    cp ${UNPACKDIR}/IW610-Q3-25-RC2-p73/sduart_iw610.bin.se ${S}/nxp/FwImage_IW610_SD
    cp ${UNPACKDIR}/IW610-Q3-25-RC2-p73/uart_iw610_bt.bin.se ${S}/nxp/FwImage_IW610_SD
    cp ${UNPACKDIR}/IW610-Q3-25-RC2-p73/uartspi_iw610.bin.se ${S}/nxp/FwImage_IW610_SD
    cp ${UNPACKDIR}/IW610-Q3-25-RC2-p73/sduartspi_iw610.bin.se ${S}/nxp/FwImage_IW610_SD
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/nxp
    oe_runmake install INSTALLDIR=${D}${nonarch_base_libdir}/firmware/nxp
}

FILES:${PN}-nxp8997-common = " \
    ${nonarch_base_libdir}/firmware/nxp/ed_mac_ctrl_V3_8997.conf \
    ${nonarch_base_libdir}/firmware/nxp/txpwrlimit_cfg_8997.conf \
    ${nonarch_base_libdir}/firmware/nxp/uart8997_bt_v4.bin \
"

FILES:${PN}-nxp9098-common = " \
    ${nonarch_base_libdir}/firmware/nxp/ed_mac_ctrl_V3_909x.conf \
    ${nonarch_base_libdir}/firmware/nxp/txpwrlimit_cfg_9098.conf \
    ${nonarch_base_libdir}/firmware/nxp/uart9098_bt_v1.bin \
"

FILES:${PN}-nxpiw610-sdio += " \
    ${nonarch_base_libdir}/firmware/nxp/sd_iw610.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sduart_iw610.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/uart_iw610_bt.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/uartspi_iw610.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sduartspi_iw610.bin.se \
    ${nonarch_base_libdir}/firmware/nxp/sdiw610_WlanCalData_ext.conf \
"
PACKAGES += "${PN}-all-sdio ${PN}-all-pcie"

RDEPENDS:${PN}-all-sdio = " \
    ${PN}-nxp8801-sdio \
    ${PN}-nxp8987-sdio \
    ${PN}-nxp8997-sdio \
    ${PN}-nxp9098-sdio \
    ${PN}-nxpiw416-sdio \
    ${PN}-nxpiw610-sdio \
    ${PN}-nxpiw612-sdio \
"

RDEPENDS:${PN}-all-pcie = " \
    ${PN}-nxp8997-pcie \
    ${PN}-nxp9098-pcie \
"

FILES:${PN}-nxpiw610-usb += " \
    ${nonarch_base_libdir}/firmware/nxp/usb*_iw610.bin.se \
"

PACKAGES += "${PN}-all-usb ${PN}-nxpiw610-usb ${PN}-nxpaw693-pcie"

RDEPENDS:${PN}-all-usb = " \
    ${PN}-nxpiw610-usb \
"

ALLOW_EMPTY:${PN}-all-sdio = "1"
ALLOW_EMPTY:${PN}-all-pcie = "1"
ALLOW_EMPTY:${PN}-all-usb = "1"
