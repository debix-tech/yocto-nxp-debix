PN = "matter-ncp"
SUMMARY = "Matter Smart Home connectivity on i.MX boards with Redfinch NCP module"
DESCRIPTION = "This layer loads the Matter applications with Redfinch NCP"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRCBRANCH = "v1.3-branch-release-matter-ncp"
IMX_MATTER_SRC ?= "gitsm://github.com/NXP/matter.git;protocol=https"
SRC_URI = "${IMX_MATTER_SRC};branch=${SRCBRANCH}"
MATTER_PY_PATH ?= "${STAGING_BINDIR_NATIVE}/python3-native/python3"

PATCHTOOL = "git"

SRCREV = "926f6c2a599cfe197180f77cb38a40094e2f09a1"

TARGET_CC_ARCH += "${LDFLAGS}"
DEPENDS += " gn-native ninja-native avahi dbus-glib-native pkgconfig-native boost python3-pip-native python3-packaging python3-click libusb "
RDEPENDS_${PN} += " libavahi-client boost boost-dev boost-staticdev libusb "
FILES:${PN} += "usr/share"

INSANE_SKIP:${PN} += "dev-so debug-deps strip"

def get_target_cpu(d):
    for arg in (d.getVar('TUNE_FEATURES') or '').split():
        if arg == "cortexa7":
            return 'arm'
        if arg == "armv8a":
            return 'arm64'
    return 'arm64'

def get_arm_arch(d):
    for arg in (d.getVar('TUNE_FEATURES') or '').split():
        if arg == "cortexa7":
            return 'armv7ve'
        if arg == "armv8a":
            return 'armv8-a'
    return 'armv8-a'

def get_arm_cpu(d):
    for arg in (d.getVar('TUNE_FEATURES') or '').split():
        if arg == "cortexa7":
            return 'cortex-a7'
        if arg == "armv8a":
            return 'cortex-a53'
    return 'cortex-a53'

TARGET_CPU = "${@get_target_cpu(d)}"
TARGET_ARM_ARCH = "${@get_arm_arch(d)}"
TARGET_ARM_CPU = "${@get_arm_cpu(d)}"

S = "${WORKDIR}/git"

common_configure() {
    PKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR} \
    PKG_CONFIG_LIBDIR=${PKG_CONFIG_PATH} \
    gn gen out/aarch64 --script-executable="${MATTER_PY_PATH}" --args='treat_warnings_as_errors=false target_os="linux" target_cpu="${TARGET_CPU}" arm_arch="${TARGET_ARM_ARCH}" arm_cpu="${TARGET_ARM_CPU}" build_without_pw=true chip_with_imx_ele=0 enable_exceptions=true chip_with_linux_ncp_host=1 chip_code_pre_generated_directory="${S}/zzz_pregencodes"
        import("//build_overrides/build.gni")
        target_cflags=[
                        "-DCHIP_DEVICE_CONFIG_WIFI_STATION_IF_NAME=\"mlan0\"",
                       ]
        custom_toolchain="${build_root}/toolchain/custom"
        target_cc="${CC}"
        target_cxx="${CXX}"
        target_ar="${AR}"'
}

do_configure() {
    cd ${S}/
    if ${DEPLOY_TRUSTY}; then
        git submodule update --init
        ./scripts/checkout_submodules.py
    fi
    cd ${S}
    touch build_overrides/pigweed_environment.gni
    cd ${S}/examples/all-clusters-app/nxp/linux_ncp

    # Configuration for IF_TYPE=1 (usb)
    PKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR} \
    PKG_CONFIG_LIBDIR=${PKG_CONFIG_PATH} \
    gn gen out/aarch64_usb --script-executable="${MATTER_PY_PATH}" --args='treat_warnings_as_errors=false target_os="linux" target_cpu="${TARGET_CPU}" arm_arch="${TARGET_ARM_ARCH}" arm_cpu="${TARGET_ARM_CPU}" build_without_pw=true chip_with_imx_ele=0 enable_exceptions=true chip_with_linux_ncp_host=1 ncp_host_interface=1 chip_code_pre_generated_directory="${S}/zzz_pregencodes"
        import("//build_overrides/build.gni")
        target_cflags=[
                        "-DCHIP_DEVICE_CONFIG_WIFI_STATION_IF_NAME=\"mlan0\"",
                       ]
        custom_toolchain="${build_root}/toolchain/custom"
        target_cc="${CC}"
        target_cxx="${CXX}"
        target_ar="${AR}"'

    # Configuration for IF_TYPE=2 (uart)
    PKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR} \
    PKG_CONFIG_LIBDIR=${PKG_CONFIG_PATH} \
    gn gen out/aarch64_uart --script-executable="${MATTER_PY_PATH}" --args='treat_warnings_as_errors=false target_os="linux" target_cpu="${TARGET_CPU}" arm_arch="${TARGET_ARM_ARCH}" arm_cpu="${TARGET_ARM_CPU}" build_without_pw=true chip_with_imx_ele=0 enable_exceptions=true chip_with_linux_ncp_host=1 ncp_host_interface=2 chip_code_pre_generated_directory="${S}/zzz_pregencodes"
        import("//build_overrides/build.gni")
        target_cflags=[
                        "-DCHIP_DEVICE_CONFIG_WIFI_STATION_IF_NAME=\"mlan0\"",
                       ]
        custom_toolchain="${build_root}/toolchain/custom"
        target_cc="${CC}"
        target_cxx="${CXX}"
        target_ar="${AR}"'

    # Configuration for IF_TYPE=3 (spi)
    PKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR} \
    PKG_CONFIG_LIBDIR=${PKG_CONFIG_PATH} \
    gn gen out/aarch64_spi --script-executable="${MATTER_PY_PATH}" --args='treat_warnings_as_errors=false target_os="linux" target_cpu="${TARGET_CPU}" arm_arch="${TARGET_ARM_ARCH}" arm_cpu="${TARGET_ARM_CPU}" build_without_pw=true chip_with_imx_ele=0 enable_exceptions=true chip_with_linux_ncp_host=1 ncp_host_interface=3 chip_code_pre_generated_directory="${S}/zzz_pregencodes"
        import("//build_overrides/build.gni")
        target_cflags=[
                        "-DCHIP_DEVICE_CONFIG_WIFI_STATION_IF_NAME=\"mlan0\"",
                       ]
        custom_toolchain="${build_root}/toolchain/custom"
        target_cc="${CC}"
        target_cxx="${CXX}"
        target_ar="${AR}"'

    # Configuration for IF_TYPE=4 (sdio)
    PKG_CONFIG_SYSROOT_DIR=${PKG_CONFIG_SYSROOT_DIR} \
    PKG_CONFIG_LIBDIR=${PKG_CONFIG_PATH} \
    gn gen out/aarch64_sdio --script-executable="${MATTER_PY_PATH}" --args='treat_warnings_as_errors=false target_os="linux" target_cpu="${TARGET_CPU}" arm_arch="${TARGET_ARM_ARCH}" arm_cpu="${TARGET_ARM_CPU}" build_without_pw=true chip_with_imx_ele=0 enable_exceptions=true chip_with_linux_ncp_host=1 ncp_host_interface=4 chip_code_pre_generated_directory="${S}/zzz_pregencodes"
        import("//build_overrides/build.gni")
        target_cflags=[
                        "-DCHIP_DEVICE_CONFIG_WIFI_STATION_IF_NAME=\"mlan0\"",
                       ]
        custom_toolchain="${build_root}/toolchain/custom"
        target_cc="${CC}"
        target_cxx="${CXX}"
        target_ar="${AR}"'
}

do_compile() {
    cd ${S}/examples/all-clusters-app/nxp/linux_ncp
    ninja -C out/aarch64_usb
    ninja -C out/aarch64_uart
    ninja -C out/aarch64_spi
    ninja -C out/aarch64_sdio
}

do_install() {
    install -d -m 755 ${D}${bindir}
    install ${S}/examples/all-clusters-app/nxp/linux_ncp/out/aarch64_usb/chip-all-clusters-app ${D}${bindir}/chip-all-clusters-app-ncp-usb
    install ${S}/examples/all-clusters-app/nxp/linux_ncp/out/aarch64_uart/chip-all-clusters-app ${D}${bindir}/chip-all-clusters-app-ncp-uart
    install ${S}/examples/all-clusters-app/nxp/linux_ncp/out/aarch64_spi/chip-all-clusters-app ${D}${bindir}/chip-all-clusters-app-ncp-spi
    install ${S}/examples/all-clusters-app/nxp/linux_ncp/out/aarch64_sdio/chip-all-clusters-app ${D}${bindir}/chip-all-clusters-app-ncp-sdio
}

INSANE_SKIP_${PN} = "ldflags"

