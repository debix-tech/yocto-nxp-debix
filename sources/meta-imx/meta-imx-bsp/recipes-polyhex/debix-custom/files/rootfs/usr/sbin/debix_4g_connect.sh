
echo -e "\033[32m =quectel start \033[0m"

rm /etc/ppp/resolv.conf
killall pppd
sleep 5
mkdir -p /var/run/pppd/lock/

pppd call quectel-ppp &

echo -e "\032[32m =quectel wait ppp0 ok \033[0m"
sleep 10 

if [ -f /etc/ppp/resolv.conf ] ; then
cat /etc/ppp/resolv.conf > /etc/resolv.conf
echo -e "\033[32m =quectel ppp0 ok \033[0m"
else
echo -e "\033[31m =quectel ppp0 err \033[0m"
fi


