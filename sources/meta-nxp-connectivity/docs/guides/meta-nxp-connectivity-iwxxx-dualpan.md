# IWxxx Thread & Zigbee Dualpan solution in NXP i.MX Matter

## Table of contents

[**Introduction**](#introduction)<br>
[**IWxxx Dualpan Architecture**](#iwxxx-dualpan-architecture)<br>
[**IWxxx Zigbee Stack and Examples**](#iwxxx-zigbee-stack-and-examples)<br>
[**Matter to Zigbee Bridge Example**](#matter-to-zigbee-bridge-example)<br>

## Introduction
The IWxxx NXP chipsets ([IW612](https://www.nxp.com/products/IW612), [IW610](https://www.nxp.com/products/IW610)) features highly integrated 2.4/5 GHz dual-band 1x1 Wi-Fi 6, Bluetooth/Bluetooth Low Energy 5.4 and 802.15.4 tri-radio single-chip solution<br>
[NXP i.MX meta-nxp-connectivity Yocto layer](https://github.com/nxp-imx/meta-nxp-connectivity/tree/master) provides Yocto recipes to enable Matter, OpenThread and Zigbee for i.MX MPU<br>
Prerequisite is to follow [instructions](https://github.com/nxp-imx/meta-nxp-connectivity/tree/master?tab=readme-ov-file#How-to-build-the-Yocto-image) to first build the complete **i.MX Matter** image

Following sections specifically describe how to experiment Zigbee & Thread Dualpan features with 3-radio IWxxx NXP chipsets e.g. IW612 or IW610<br>
Dedicated IWxxx Zigbee stack and examples are first introduced<br>
Finally, last section details a complete IWxxx Dualpan example on i.MX: Matter to Zigbee Bridge Application<br>

## IWxxx Dualpan Architecture

![i.MX Dualpan IWxxx](../images/zigbee/iwxxx_dualpan_architecture.png)

Thread and Zigbee i.MX Host applications run on top of a SPI Multiplexer daemon called **zb_mux**<br>
The zb_mux daemon uses i.MX SPI kernel device to exchange Spinel messages with IWxxx Firmware through SPI Bus<br>
The zb_mux daemon also creates two Virtual UART Devices that both Zigbee and Thread applications use to communicate seamlessly with IWxxx Firmware<br>

## IWxxx Zigbee Stack and Examples

meta-nxp-connectivity comes with a certified Zigbee stack for IWxxx chipsets<br>
It is split in two Yocto recipes:<br>

* [zigbee-rcp-sdk](https://github.com/nxp-imx/meta-nxp-connectivity/tree/master/meta-nxp-zigbee-rcp/recipes-zigbee-rcp/recipes-zigbee-rcp-sdk): zb_mux daemon, IWxxx Zigbee Stack header files and static libraries and Linux Systemd services&scripts, IWxxx Zigbee Stack Development Guide documentation<br>
* [zigbee-rcp-apps](https://github.com/nxp-imx/meta-nxp-connectivity/tree/master/meta-nxp-zigbee-rcp/recipes-zigbee-rcp/recipes-zigbee-rcp-apps): Zigbee Example applications C source code and CMAKE build files<br>

> **_NOTE:_**
Additional important resources can be accessed from NXP website. It requires login and granted access to these resources:
Latest **IW612 Zigbee DualPan package** is available [SD-WLAN-UART-BT-SPI-OT-Zigbee-DualPAN-IW612-LNX_6_12_20-IMX8-18.99.3.p25.7-18.99.3.p25.7-MM6X18537.p9-GPL](https://www.nxp.com/webapp/sps/download/license.jsp?colCode=SD-WLAN-UART-BT-SPI-IW612-LNX_6_12_20-MM6X18537-GP&appType=file1&location=null&DOWNLOAD_ID=null)<br>
It includes:
    - README_Zboss_package_for_Zigbee.txt
    - Example applications source code


### Zigbee SDK

Once the **i.MX Matter** image is built, all **zigbee-rcp-sdk** components can be found in the Yocto build folder<br>
For example, for a *MACHINE=imx93evk-iwxxx-matter* build, components are unpacked in *${MY_YOCTO}/bld-xwayland-imx93evk-iwxxx-matter/tmp/work/armv8a-poky-linux/zigbee-rcp-sdk/1.0/sources-unpack/*
**zigbee-rcp-sdk** goal is to:

* install zb_mux daemon and Systemd Zigbee services on the i.MX Root Filesystem<br>
* install Zigbee header files and static libraries in Yocto build system to build Zigbee example applications<br>
* install IWxxx Zigbee Stack Development Guide documentation

### Zigbee Examples

Similarly to **zigbee-rcp-sdk** components, **zigbee-rcp-apps** components can be found in the Yocto build folder<br>
For example, for a *MACHINE=imx93evk-iwxxx-matter* build, components are unpacked in *${MY_YOCTO}/bld-xwayland-imx93evk-iwxxx-matter/tmp/work/armv8a-poky-linux/zigbee-rcp-apps/1.0/sources-unpack/*
The folder tree content is:<br>

```bash
├── CMakeLists.txt
├── examples
│   ├── hello
│       ├── hello.c
│       ├── CMakeLists.txt
── zigbee_rcp_app.cmake
```

#### Zigbee Application Details

* CMakeList.txt: contains all the applications to be built by pointing to the individual "Application CMakeLists.txt" of an application folder
* zigbee_rcp_app.cmake: builds either a ZC_EXE, a ZR_EXE or a ZED_EXE according settings from each individual "Application CMakeLists.txt"

Taking examples/hello, its CMakeLists.txt is:

```bash
set(app_proj "examples/hello")
project(${app_proj})
message("Project: ${app_proj}")

set(zc_exe "hello_zc")
list(APPEND zc_src ${app_proj}/hello.c)
option(ZC_EXE "Build ZC" ON)

set(zr_exe "hello_zr")
list(APPEND zr_src  ${app_proj}/hello.c)
option(ZR_EXE "Build ZR" ON)

set(zed_exe "hello_zed")
list(APPEND zed_src ${app_proj}/hello.c)
option(ZED_EXE "Build ZED" ON)

include(${CMAKE_CURRENT_SOURCE_DIR}/zigbee_rcp_app.cmake)
```

zigbee_rcp_app.cmake build results are:
|Executable<br>in /usr/bin|Zigbee<br>Device Type|Source files|Zigbee<br>Static libraries|
|:-------|:-------|:-------|:-------|
|hello_zc|Coordinator|hello.c|libzboss.a|
|hello_zr|Router|hello.c|libzboss.a|
|hello_zed|End Device|hello.c|libzboss.ed.a|

#### Import an existing Zigbee application into meta-nxp-connectivity
From the latest **IW612 Zigbee DualPan package** mentioned above, NXP-ZBOSS-HOST-RELEASE-XXX.YYY-aaaaaa-bbbbbb.zip contains the "examples" folder<br>
All these examples applications source codes can be easily integrated in the meta-nxp-connectivity to get them in the final i.MX Root Filesystem<br>
<br>
Following describes how to import and build examples/cli_nxp<br>
examples/cli_nxp/Makefile helps to build five executables: cli_nxp_zczr & cli_nxp_zed and zb_daemon_zczr & zb_daemon_zed & zb-ctl<br>
As an example, zb_daemon_zczr requires the following object files:<br>
OBJS_ZB_DAEMON_ZCZR = cli_main.o cli_menu_socket.o cli_config.o cli_network.o cli_endpoint.o cli_cluster.o cli_zdo.o cli_installcode.o cli_nvram.o cli_tools.o<br>

All build directive from Makefile needs to be translated into Cmake build directives located in CMakeLists.txt

```bash
cd ${MY_YOCTO}/sources/meta-nxp-connectivity/meta-nxp-zigbee-rcp/recipes-zigbee-rcp-apps/files/
mkdir examples/cli_nxp
cp <NXP-ZBOSS-HOST-RELEASE-XXX.YYY-aaaaaa-bbbbbb>/examples/cli_nxp/ examples/cli_nxp
mkdir examples/ota_upgrade_nxp
cp <NXP-ZBOSS-HOST-RELEASE-XXX.YYY-aaaaaa-bbbbbb>/examples/ota_upgrade_nxp/ examples/ota_upgrade_nxp

cat << EOF > examples/cli_nxp/CMakeLists.txt
cmake_minimum_required(VERSION VERSION 3.10.2)

set(zc_src "")
set(zr_src "")
set(zed_src "")
set(zc_exe "")
set(zr_exe "")
set(zed_exe "")

set(app_proj "examples/cli_nxp")
project(\${app_proj})
message("Project: \${app_proj}")

set(zc_exe "cli_nxp_zczr")
list(APPEND zc_src \${app_proj}/cli_main.c \${app_proj}/cli_menu_console.c \${app_proj}/cli_config.c \${app_proj}/cli_network.c \${app_proj}/cli_endpoint.c \${app_proj}/cli_cluster.c \${app_proj}/cli_zdo.c \${app_proj}/cli_installcode.c \${app_proj}/cli_nvram.c \${app_proj}/cli_tools.c)
option(ZC_EXE "Build ZC" ON)

set(zed_exe "cli_nxp_zed")
list(APPEND zed_src \${app_proj}/cli_main.c \${app_proj}/cli_menu_console.c \${app_proj}/cli_config.c \${app_proj}/cli_network.c \${app_proj}/cli_endpoint.c \${app_proj}/cli_cluster.c \${app_proj}/cli_zdo.c \${app_proj}/cli_installcode.c \${app_proj}/cli_nvram.c \${app_proj}/cli_tools.c)
option(ZED_EXE "Build ZED" ON)

include(\${CMAKE_CURRENT_SOURCE_DIR}/zigbee_rcp_app.cmake)

set(zc_src "")
set(zr_src "")
set(zed_src "")
set(zc_exe "")
set(zr_exe "")
set(zed_exe "")

set(zc_exe "zb_daemon_zczr")
list(APPEND zc_src \${app_proj}/cli_main.c \${app_proj}/cli_menu_socket.c \${app_proj}/cli_config.c \${app_proj}/cli_network.c \${app_proj}/cli_endpoint.c \${app_proj}/cli_cluster.c \${app_proj}/cli_zdo.c \${app_proj}/cli_installcode.c \${app_proj}/cli_nvram.c \${app_proj}/cli_tools.c)
option(ZC_EXE "Build ZC" ON)

set(zed_exe "zb_daemon_zed")
list(APPEND zed_src \${app_proj}/cli_main.c \${app_proj}/cli_menu_socket.c \${app_proj}/cli_config.c \${app_proj}/cli_network.c \${app_proj}/cli_endpoint.c \${app_proj}/cli_cluster.c \${app_proj}/cli_zdo.c \${app_proj}/cli_installcode.c \${app_proj}/cli_nvram.c \${app_proj}/cli_tools.c)
option(ZED_EXE "Build ZED" ON)

set(zr_exe "zb-ctl")
list(APPEND zr_src \${app_proj}/zb-ctl.c)
option(ZR_EXE "Build ZR" ON)

include(\${CMAKE_CURRENT_SOURCE_DIR}/zigbee_rcp_app.cmake)
EOF

echo "include(\${CMAKE_CURRENT_SOURCE_DIR}/examples/cli_nxp/CMakeLists.txt)" >> CMakeLists.txt

echo "SRC_URI += \"file://examples/cli_nxp/\"" >> ../zigbee-rcp-apps.bb
echo "SRC_URI += \"file://examples/ota_upgrade_nxp/\"" >> ../zigbee-rcp-apps.bb

cd ${MY_YOCTO}/bld-xwayland-imx93evk-iwxxx-matter
bitbake -fc cleanall zigbee-rcp-apps
bitbake zigbee-rcp-apps

ls -l tmp/work/armv8a-poky-linux/zigbee-rcp-apps/1.0/image/usr/bin/cli_nxp_zczr
ls -l tmp/work/armv8a-poky-linux/zigbee-rcp-apps/1.0/image/usr/bin/cli_nxp_zed
ls -l tmp/work/armv8a-poky-linux/zigbee-rcp-apps/1.0/image/usr/bin/zb_daemon_zczr
ls -l tmp/work/armv8a-poky-linux/zigbee-rcp-apps/1.0/image/usr/bin/zb_daemon_zed
ls -l tmp/work/armv8a-poky-linux/zigbee-rcp-apps/1.0/image/usr/bin/zb-ctl

```

#### Create a new Zigbee application
From the latest **IW612 Zigbee DualPan package** mentioned above, NXP-ZBOSS-HOST-RELEASE-XXX.YYY-aaaaaa-bbbbbb.zip contains the "examples" folder<br>
the examples/dualpan_nxp currently builds 3 executables: dualpan_zc, dualpan_zr and dualpan_zed<br>

Based on examples/dualpan_nxp, the new application is **example/my_zigbee_gateway** with only one Zigbee Coordinator executable: **my_zigbee_gw_zc**
```bash
cd ${MY_YOCTO}/sources/meta-nxp-connectivity/meta-nxp-zigbee-rcp/recipes-zigbee-rcp-apps/files/
mkdir examples/my_zigbee_gateway
cp <NXP-ZBOSS-HOST-RELEASE-XXX.YYY-aaaaaa-bbbbbb>/examples/dualpan_nxp/dualpan_zc.c examples/my_zigbee_gateway/my_gw_zc.c
cp <NXP-ZBOSS-HOST-RELEASE-XXX.YYY-aaaaaa-bbbbbb>/examples/dualpan_nxp/dualpan_channel.h examples/my_zigbee_gateway/
cp <NXP-ZBOSS-HOST-RELEASE-XXX.YYY-aaaaaa-bbbbbb>/examples/dualpan_nxp/dualpan.h examples/my_zigbee_gateway/

cat << EOF > examples/my_zigbee_gateway/CMakeLists.txt
cmake_minimum_required(VERSION VERSION 3.10.2)

set(zc_src "")
set(zr_src "")
set(zed_src "")
set(zc_exe "")
set(zr_exe "")
set(zed_exe "")

set(app_proj "examples/my_zigbee_gateway")
project(\${app_proj})
message("Project: \${app_proj}")

set(zc_exe "my_zigbee_gw_zc")
list(APPEND zc_src \${app_proj}/my_gw_zc.c \${app_proj}/dualpan_channel.c)
option(ZC_EXE "Build ZC" ON)

include(\${CMAKE_CURRENT_SOURCE_DIR}/zigbee_rcp_app.cmake)
EOF

echo "include(\${CMAKE_CURRENT_SOURCE_DIR}/examples/my_zigbee_gateway/CMakeLists.txt)" >> CMakeLists.txt

echo "SRC_URI += \"file://examples/my_zigbee_gateway/\"" >> ../zigbee-rcp-apps.bb

cd ${MY_YOCTO}/bld-xwayland-imx93evk-iwxxx-matter
bitbake -fc cleanall zigbee-rcp-apps
bitbake zigbee-rcp-apps

ls -l tmp/work/armv8a-poky-linux/zigbee-rcp-apps/1.0/image/usr/bin/my_zigbee_gw_zc
```
After a direct transfer in /usr/bin folder of i.MX, new **my_zigbee_gw_zc** executable is available



## Matter to Zigbee Bridge Example

[MatterZigbeeRcp-bridge](https://github.com/NXP/matter/tree/v1.4-branch-nxp_imx_2025_q1/examples/bridge-app/nxp/linux-M2ZigbeeRcp-bridge) application demonstrates the complete Matter example on i.MX93 & IW612<br>
![i.MX becomes a Matter to Zigbee Bridge](../images/zigbee/MatterToZigbee-Bridge.png)

The i.MX Matter image allows to have a complete Matter Controller on one single i.MX93&IW612 platform:<br>

* [Embedded Posix Openthread BorderRouter](https://github.com/nxp-imx/meta-nxp-connectivity/tree/master/meta-nxp-otbr/recipes-otbr/otbr-iwxxx) manages a Thread network and provides Ethernet or Wi-Fi networks connectivity<br>
* [Matter chip-tool](https://github.com/NXP/matter/tree/v1.4-branch-nxp_imx_2025_q1/examples/chip-tool) is used to commission and control Matter Wireless End-Devices, either on Wi-Fi or on Thread networks<br>
* [M2ZigbeeRcp-bridge](https://github.com/NXP/matter/tree/v1.4-branch-nxp_imx_2025_q1/examples/bridge-app/nxp/linux-M2ZigbeeRcp-bridge#readme) is a Zigbee Coordinator allowing Zigbee End-Devices to join, and transforming them into Matter Bridged End-Devices<br>

> **_NOTE:_**
Both meta-nxp-connectivity native otbr-agent-iwxxx and chip-tool executables are not intended to be modified<br>
**M2ZigbeeRcp-bridge** is currently an **example of a Matter to Zigbee bridge**<br>
It is intended to be modified, and its features improved as explained in the [dynamic-endpoint-control](https://github.com/NXP/matter/blob/v1.4-branch-nxp_imx_2025_q1/examples/bridge-app/nxp/linux-M2ZigbeeRcp-bridge/README.md#dynamic-endpoint-control) section<br>
