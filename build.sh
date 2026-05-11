### imx93 BMB-13-A1
EULA=1 DISTRO=fsl-imx-xwayland MACHINE=imx93-bmb-13-a1 source imx-setup-release.sh -b imx93
### imx91 BMB-13-A1
EULA=1 DISTRO=fsl-imx-xwayland MACHINE=imx91-bmb-13-a1 source imx-setup-release.sh -b imx91

bitbake imx-image-full
