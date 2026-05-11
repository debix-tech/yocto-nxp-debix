# New features
A summary of the main new features is as follows.
New features added for all supported boards:
• Upgraded the kernel to 6.12.49 with consolidated Linux Factory Kernel.
• Updated EULA to v63 May 2025.
• Supports the GCC 14.3 toolchain.
• Supports Glitch Detection (GDET) on i.MX 93.
• Cortex-M33 update for 8ULP, i.MX 93, and i.MX 943; Cortex-M7 updates for i.MX 8M Nano, i.MX 8M Plus,
i.MX 95, and i.MX 943; Cortex-M4 update for i.MX 7ULP, i.MX 8M Mini, and i.MX 8M Quad.
• Security
– OP-TEE upgraded to 4.8.0.
Note: With this upgrade, due to security improvements in upstream OP-TEE, xtest takes more time to
complete.
• Multimedia updates:
– Upgraded GStreamer to 1.26.6.
• Graphics updates:
– i.MX 9 GPU driver upgraded to r54p1, which starts to support Vulkan1.4.
– i.MX 6/7/8 GPU driver upgraded to 6.4.1.p4.2 with Vulkan enablement, bug fixes, performance
optimizations, NPU mmuSwSwitch feature (optional) and mitigations added to address the CVE-2025-66425
issue.
– GPU SDK upgraded to 6.5.0.
– Chromium upgraded to 129.0.6668.100.
Note: Chromium is not supported on i.MX 6 and i.MX 7 any longer due to the DRM/KMS display driver
limitation, but is still supported on i.MX 8 and i.MX 9.
– Added Chromium support for i.MX 93 and i.MX 943.
Note: Software only. No GPU hardware acceleration and WebGL support.
– Added Flutter support for i.MX 93 with Mesa RVGPU accelerator.
Note: The RVGPU accelerator is a GPU emulator running on the i.MX platform.
• Machine Learning updates:
– OpenCV upgraded to 4.11.0.
– TensorFlow Lite upgraded to 2.19 with GPU acceleration.
– i.MX 93 Vela upgraded to 4.3.0.
• Yocto updates:
– Toaster is supported. For details, see the i.MX Yocto Project User's Guide (UG10164).
• i.MX 8M Plus
– Updates for ISP 4.2.2.25.4.
• i.MX 91
– SoC revision A1 is supported, and revision A0 is deprecated.
i.MX 95
– SoC revision B0 is supported, and revision A0/A1 is not supported anymore.
• i.MX 943
– Supports i.MX 943 19x19 LPDDR5 EVK, i.MX 943 19x19 LPDDR4 EVK, and i.MX 943 15x15 LPDDR4
EVK.
• Arm SystemReady-IR (SR-IR) certification
– i.MX 8M Mini EVK board has passed the Arm SR-IR certification.
– i.MX 8M Plus EVK board has passed the Arm SR-IR certification.
– i.MX 8M Quad EVK board has passed the Arm SR-IR certification.
– i.MX 8M Nano EVK board has passed the Arm SR-IR certification. 
• Supports the Cyber Resilient Recovery Module (CRRM).
• KernelSpace XDP based xdp_fp application
– Supports CMM with xdp_fp on i.MX 95 and i.MX 943.
• The following boards are not supported in this release:
– i.MX 8QuadXPlus B0 MEK
– i.MX 8DXL A1 LPDDR4 EVK, A1 DDR3L EVK
– i.MX 6QuadPlus SABRE-AI
– i.MX 6Quad/Dual SABRE-AI
– i.MX 6DualLite SABRE-AI

# Host Setup
To achieve the expected behavior of the Yocto Project on a Linux host machine, install the packages and utilities described below. An important consideration is the hard disk space required in the host machine. For example, when building on a machine running Ubuntu, the minimum hard disk space required is about 50 GB. It is recommended that at least 120 GB is provided, which is enough to compile all backends together. For building machine learning components, at least 250 GB is recommended.

The recommended minimum Ubuntu version is 22.04 or later

### 1. Host packages
```bash
$ sudo apt-get install build-essential chrpath cpio debianutils diffstat file gawk gcc git iputils-ping libacl1 liblz4-tool locales python3 python3-git python3-jinja2 python3-pexpect python3-pip python3-subunit socat texinfo unzip wget xz-utils zstd efitools

```

### 2. Build configurations
```
$ DISTRO=<distro name> MACHINE=<machine name> source imx-setup-release.sh -b
 <build dir>

eg.
build debix imx93 bmb 13 a1
$ EULA=1 DISTRO=fsl-imx-xwayland MACHINE=imx93-bmb-13-a1 source imx-setup-release.sh -b imx93
$ bitbake imx-image-full
```

 
