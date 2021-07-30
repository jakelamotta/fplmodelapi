mysql -ppassword -uroot -P $1 <<EOF

use fpl;

select player, (xpabs-1.1838553306461845)/cost as c , team 
from calculatedplayerstatistics cps
where cps.position like "F%"
and cps.minutesplayed > 300
order by c desc
limit 20;

EOF
