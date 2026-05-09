#!/bin/bash
 
BASE_DIR="/run/udev/data/btnxpuart"
DEVICE_NAME="$1"
EVENT_VALUE="$2"
 
if [[ -z "$DEVICE_NAME" || -z "$EVENT_VALUE" ]]; then
    echo "Error: Missing environment variables" >&2
    exit 1
fi
 
# Write the event value to the "state" file
echo "$EVENT_VALUE" > "${BASE_DIR}:${DEVICE_NAME}.state"
exit 0
