FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://override.conf"
SRC_URI += "file://gatt_send_notify.patch"

do_install:append() {
    install -d ${D}${systemd_unitdir}/system/bluetooth.service.d
    install -m 0644 ${UNPACKDIR}/override.conf ${D}${systemd_unitdir}/system/bluetooth.service.d/override.conf
}

FILES:${PN} += "${systemd_unitdir}/system/bluetooth.service.d/override.conf"
