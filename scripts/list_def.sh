mysql -ppassword -uroot -P $1 <<EOF

use fpl;

select player, (xpabs-2.1156827937830642)/cost as c , team 
from calculatedplayerstatistics cps
where cps.position like "D%"
and cps.minutesplayed > 300
order by c desc
limit 20;

EOF
