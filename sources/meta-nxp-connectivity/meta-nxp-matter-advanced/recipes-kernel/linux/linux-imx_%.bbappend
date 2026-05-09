FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "${@bb.utils.contains_any('MACHINE_FEATURES', 'trusty', 'file://0001-MATTER-3026-1-enable-Trusty-OS-for-matter.patch', '', d)}"
SRC_URI += "${@bb.utils.contains_any('MACHINE_FEATURES', 'trusty', 'file://0001-arm64-dts-Add-i.MX8MP-Trusty-OS-device-tree-config.patch', '', d)}"
SRC_URI += "${@bb.utils.contains('MACHINE_FEATURES', 'trusty', 'file://0002-MATTER-3026-2-enable-Trusty-OS-for-matter.patch', '', d)}"
SRC_URI += "${@bb.utils.contains('MACHINE_FEATURES', 'trusty', 'file://0001-Add-MCU-NCP-Host-driver.patch', '', d)}"
SRC_URI += "${@bb.utils.contains('MACHINE_FEATURES', 'trusty', 'file://kernel-config/Enable-Trusty.cfg', '', d)}"
SRC_URI += "${@bb.utils.contains('MACHINE_FEATURES', 'has-matter-ncp', 'file://kernel-config/Enable-NCP-Host.cfg', '', d)}"
